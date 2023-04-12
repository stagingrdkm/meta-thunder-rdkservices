# List of required COMRPC and JSONRPC interfaces to generate and compile by wpeframework-interfaces project

########## commmon interfaces ##########
INTERFACES_PATTERNS += "Ids.h IMemory.h"
JSONRPC_PATTERNS    += "common.json TraceControl.json SecurityAgent.json"

########## browser interfaces ##########
INTERFACES_PATTERNS += "IBrowser.h IApplication.h"
JSONRPC_PATTERNS    += "Browser.json WebKitBrowser.json StateControl.json"

########## rdkservice-ocdm interfaces ##########
INTERFACES_PATTERNS += "${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'IDRM.h IContentDecryption.h', '', d)}"
JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'OCDM.json', '', d)}"

########## onemwfirebolt interfaces ##########

### rdkservice-displayinfo ###
INTERFACES_PATTERNS += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'IDisplayInfo.h', '', d)}"
JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'DisplayInfo.json', '', d)}"

### rdkservice-playerinfo (plugin is disabled) ###
# INTERFACES_PATTERNS += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'IPlayerInfo.h IDolby.h', '', d)}"
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'PlayerInfo.json', '', d)}"

### rdkservice-deviceinfo ###
# no COMRPC interfaces exposed via COMRPC for rdkservice-deviceinfo
JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'DeviceInfo.json', '', d)}"

########## onemwdac interfaces ##########

### rdkservice-fireboltmediaplayer ###
INTERFACES_PATTERNS  += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'IMediaPlayer.h', '', d)}"
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'It is done manually for Firebolt Media Player - no explict interface file - not integrated with wpeframework-interfaces', '', d)}"

### rdkservice-lisa ###
INTERFACES_PATTERNS += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'ILISA.h', '', d)}"
JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'LISA.json', '', d)}"

### rdkservice-ocicontainer ###
# no COMRPC interfaces exposed for rdkservice-ocicontainer
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'It is done manually, OCIContainer.json in plugin dir - not integrated with interfaces', '', d)}"


########## system-lgi-rdkservices ##########

# no COMRPC interfaces exposed by system-lgi-rdkservices: rdkservice-lgidisplaysettings, rdkservice-lgihdcpprofile, rdkservice-lgi-hdmicec)
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'It is done manually, LgiDisplaySettings.json in plugin dir - not integrated with wpeframework-interfaces', '', d)}"
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'It is done manually, LgiHdcpProfile.json in plugin dir - not integrated with wpeframework-interfaces', '', d)}"
# JSONRCP_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'It is done manually, LgiHdmiCec.json in plugin dir - not integrated with wpeframework-interfaces', '', d)}"

### rdkservice-xcast
# no COMRPC interfaces exposed for rdkservice-xcast
# JSONRPC_PATTERNS    += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'It is done manually, XCast.json in plugin dir - not integrated with wpeframework-interfaces', '', d)}"

