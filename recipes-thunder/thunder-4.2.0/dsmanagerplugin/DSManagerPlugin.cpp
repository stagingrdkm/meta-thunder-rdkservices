#include "DSManagerPlugin.h"
#include "systemddbusclient.h"

#include "log.h"

namespace WPEFramework {
    std::atomic<int> DSManagerPlugin::clientCount(0);
    std::atomic<bool> DSManagerPlugin::initialized{false};
    std::unique_ptr<SystemdDBusClient> DSManagerPlugin::systemdClient{nullptr};

    DSManagerPlugin::DSManagerPlugin()
    {
        int prevCount = clientCount.fetch_add(1, std::memory_order_relaxed);
        LOGDBG ("clientCount: %d -> %d", prevCount, prevCount+1);
        if (prevCount == 0) {
            tryToInitializeDS();
            systemdClient.reset(new SystemdDBusClient("process.iarm.DSMgr", "/org/freedesktop/systemd1/unit/dsmgr_2eservice", "process.iarm.Thunder_Plugins.systemdcaller"));

            systemdClient->init(
                [this](){
                    LOGINF ("dsStart");
                    tryToInitializeDS();
                },
                [this](){
                    LOGINF ("dsStop");
                    tryToDeInitializeDS();
                }
            );
        }
    }

    DSManagerPlugin::~DSManagerPlugin()
    {
        int prevCount = clientCount.fetch_sub(1, std::memory_order_relaxed);
        LOGDBG ("clientCount: %d -> %d", prevCount, prevCount-1);
        if (prevCount == 1) {
            systemdClient.reset();
            tryToDeInitializeDS();
        }
    }

    void DSManagerPlugin::tryToInitializeDS() {
        LOGDBG ("try to initialize DS");
        if(!initialized.exchange(true)){
            try
            {
                device::Manager::Initialize();
                LOGINF("device::Manager::Initialize success");
            }
            catch(...)
            {
                LOGERR("device::Manager::Initialize failed");
            }
        }
        else {
            LOGDBG ("DS already initialized");
        }
    }

    void DSManagerPlugin::tryToDeInitializeDS() {
        LOGDBG ("try to deinitialize DS");
        if(initialized.exchange(false)){
            try
            {
                device::Manager::DeInitialize();
                LOGINF("device::Manager::DeInitialize success");
            }
            catch(...)
            {
                LOGERR("device::Manager::DeInitialize failed");
            }
        }
        else
        {
            LOGDBG ("DS already deinitialized");
        }
    }
}
