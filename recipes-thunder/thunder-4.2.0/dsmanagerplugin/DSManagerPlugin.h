#ifndef DSMANAGER_PLUGIN_H
#define DSMANAGER_PLUGIN_H

#include <atomic>
#include <memory>

#include "Module.h"
#include "manager.hpp"

class SystemdDBusClient;

namespace WPEFramework {
    class EXTERNAL DSManagerPlugin {
    public:
        DSManagerPlugin();
        virtual ~DSManagerPlugin();

    private:
        void tryToInitializeDS();
        void tryToDeInitializeDS();

    private:
        static std::atomic<bool> initialized;
        static std::atomic<int> clientCount;
        static std::unique_ptr<SystemdDBusClient> systemdClient;
    };
}

#endif //DSMANAGER_PLUGIN_H
