#include "systemddbusclient.h"
#include "log.h"

namespace {
    const char* systemdManagerDbusInterface = "org.freedesktop.DBus.Properties";
    const char* propertiesChangedSignal = "PropertiesChanged";

    SystemdDBusClient::State to_state(const std::string &state) {
        using State = SystemdDBusClient::State;
        static std::map<std::string, State> stateMap = {
            {"active", State::active},
            {"reloading", State::reloading},
            {"inactive", State::inactive},
            {"failed", State::failed},
            {"activating", State::activating},
            {"deactivating", State::deactivating}
        };
        try {
            return stateMap.at(state);
        } catch (std::out_of_range &e) {
            LOGERR("Cannot convert state: %s", state.c_str());
            return State::failed;
        }
    }
}

SystemdDBusClient::SystemdDBusClient(const std::string &serviceName, const std::string &servicePath, const std::string &callerName):
    DefaultDBusClient("org.freedesktop.systemd1",
                    servicePath,
                    systemdManagerDbusInterface,
                    callerName,
                    false),
    serviceName(serviceName),
    servicePath(servicePath),
    previousState(State::active)
{
}

SystemdDBusClient::~SystemdDBusClient()
{
    auto status = removeSignalListener(propertiesChangedSignal);
    if(dbus::DBUS_STATUS_OK != status) {
        LOGERR("Cannot remove signal listener - status %d", status);
    }
}

dbus::dbus_status_t SystemdDBusClient::init(std::function<void()> startCallback, std::function<void()> stopCallback)
{
    auto ret = dbus::DefaultDBusClient::init();

    auto callWrap = [this, startCallback, stopCallback](dbus::DBusClient* dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr) {
        State state = this->getCurrentState(dbus_client_sptr, msg_sptr);

        if(previousState != state){
            if(state == State::inactive) {
                stopCallback();
            }
            else if (state == State::active) {
                startCallback();
            }
        }

        previousState = state;
    };

    listener = std::make_shared<DBusListener>(callWrap);
    addSignalListener(servicePath, systemdManagerDbusInterface, propertiesChangedSignal, listener);
    return ret;
}

bool SystemdDBusClient::extractStateAndJob(dbus::dbus_msg_sptr_t msg_sptr, std::string &state, std::pair<uint32_t, std::string> &job) {
    bool status = true;
    if (initReply(msg_sptr)) {
        auto msg_impl_sptr = getMsgImpl(msg_sptr);
        if (DBUS_TYPE_ARRAY != dbus_message_iter_get_arg_type(msg_impl_sptr->args_itr())) {
            LOGERR("Argument is not array!");
            status = false;
        } else {
            DBusMessageIter array;
            dbus_message_iter_recurse(msg_impl_sptr->args_itr(), &array);
            while (dbus_message_iter_get_arg_type(&array) != DBUS_TYPE_INVALID) {
                if (DBUS_TYPE_DICT_ENTRY != dbus_message_iter_get_arg_type(&array)) {
                    LOGERR("Argument is not a dict entry!");
                    status = false;
                } else {
                    DBusMessageIter dictEntry;
                    dbus_message_iter_recurse(&array, &dictEntry);
                    if (DBUS_TYPE_STRING == dbus_message_iter_get_arg_type(&dictEntry))
                    {
                        char *key, *value;
                        dbus_message_iter_get_basic(&dictEntry, &key);
                        dbus_message_iter_next(&dictEntry);
                        DBusMessageIter variantEntry;
                        dbus_message_iter_recurse(&dictEntry, &variantEntry);

                        if(key == std::string("Job") && DBUS_TYPE_STRUCT == dbus_message_iter_get_arg_type(&variantEntry)) {
                            DBusMessageIter jobStruct;
                            uint32_t id;
                            const char* path;
                            dbus_message_iter_recurse(&variantEntry, &jobStruct);
                            dbus_message_iter_get_basic(&jobStruct, &id);
                            dbus_message_iter_next(&jobStruct);
                            dbus_message_iter_get_basic(&jobStruct, &path);
                            job.first = id;
                            job.second = path;
                        }

                        if(DBUS_TYPE_STRING == dbus_message_iter_get_arg_type(&variantEntry)) {
                            dbus_message_iter_get_basic(&variantEntry, &value);
                            if(std::string(key) == "ActiveState") {
                                state = value;
                            }
                        }
                        dbus_message_iter_next(&variantEntry);
                    } else {
                        LOGERR("Key is not a string!");
                        status = false;
                    }
                }
                dbus_message_iter_next(&array);
            }
        }
    } else {
        status = false;
    }
    return status;
}

SystemdDBusClient::State SystemdDBusClient::getCurrentState(dbus::DBusClient* dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr)
{
    auto ptr = dbus::DefaultDBusClient::getMsgImpl(msg_sptr);
    std::string path = dbus_message_get_path (ptr->get());
    State state = previousState;

    if(path == servicePath) {
        std::string value;
        dbus_client_sptr->getReply(msg_sptr, value);

        if(value == "org.freedesktop.systemd1.Unit") {
            std::string stringState;
            std::pair<uint32_t, std::string> job;
            extractStateAndJob(msg_sptr, stringState, job);
            state = to_state(stringState);
            LOGDBG("State: %s, job: %u %s", stringState.c_str(), job.first, job.second.c_str());
        }
    }
    return state;
}
