#ifndef SYSTEMDDBUSCLIENT_H
#define SYSTEMDDBUSCLIENT_H

#include <stdexcept>
#include <string>
#include <deque>
#include <utility>
#include <map>

#include <dbus-client/DefaultDBusClient.h>

#include "dbuslistener.h"

class SystemdDBusClient final: public dbus::DefaultDBusClient
{
public:
    enum class State {
        active, reloading, inactive, failed, activating, deactivating
    };
public:
    SystemdDBusClient(const std::string &serviceName, const std::string &servicePath, const std::string &callerName);
    virtual ~SystemdDBusClient();
    dbus::dbus_status_t init(std::function<void()> startCallback, std::function<void()> stopCallback);

private:
    State getCurrentState(dbus::DBusClient* dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr);
    bool extractStateAndJob(dbus::dbus_msg_sptr_t msg_sptr, std::string &state, std::pair<uint32_t, std::string> &job);

private:
    std::string serviceName;
    std::string servicePath;
    State previousState;
    std::shared_ptr<DBusListener> listener;
};

#endif // SYSTEMDDBUSCLIENT_H
