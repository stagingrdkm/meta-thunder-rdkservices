#include "dbuslistener.h"
#include "log.h"

using std::string;

DBusListener::DBusListener(Callback callback):
    callback(callback)
{
}

void DBusListener::notify(dbus::DBusClient *dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr) {
    callback(dbus_client_sptr, msg_sptr);
}
