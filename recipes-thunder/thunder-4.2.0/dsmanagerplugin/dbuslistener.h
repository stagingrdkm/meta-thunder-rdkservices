#ifndef DBUSLISTENER_H
#define DBUSLISTENER_H

#include <string>
#include <map>
#include <functional>

#include <dbus-client/DefaultDBusClient.h>

using Callback = std::function<void(dbus::DBusClient* dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr)>;

class DBusListener final: public dbus::DBusClient::SignalListener {
public:
    explicit DBusListener(Callback callback);
    void notify(dbus::DBusClient* dbus_client_sptr, dbus::dbus_msg_sptr_t msg_sptr) override;
private:
    Callback callback;
};


#endif // DBUSLISTENER_H
