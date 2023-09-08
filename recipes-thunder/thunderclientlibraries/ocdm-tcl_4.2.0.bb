SUMMARY = "OCDM client library"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder_clientlibraries_${PV}.inc

SRC_URI += "file://0001-OCDM-enhancement-for-rdk-ocdm-adapter.patch"
SRC_URI += "file://R4.1_compilation_error_fix_temp.patch"
SRC_URI += "file://0003_MediaType_name_changed.patch"
SRC_URI += "file://0001-OCDM-enhancement-for-ocdm-adapter.patch"
SRC_URI += "file://0001-fixing-netflix-ocdm-playback-failure.patch"
SRC_URI += "file://0001-OMWAPPI-1634-align-metrics-API.patch"

RDEPENDS_${PN}_append_dunfell = "${@bb.utils.contains('DISTRO_FEATURES', 'sage_svp', ' gst-svp-ext', '', d)}"
RDEPENDS_${PN}_append_dunfell = "${@bb.utils.contains('DISTRO_FEATURES', 'rdk_svp', ' gst-svp-ext', '', d)}"

def get_cdmi_adapter(d):
    if bb.utils.contains("DISTRO_FEATURES", "nexus_svp", "true", "false", d) == "true":
        return "opencdmi_brcm_svp"
    elif bb.utils.contains("DISTRO_FEATURES", "sage_svp", "true", "false", d) == "true":
        return "opencdmi_comcast_svp"
    elif bb.utils.contains("DISTRO_FEATURES", "rdk_svp", "true", "false", d) == "true":
        return "opencdmi_rdk_svp"
    else:
        return "opencdm_gst"
    fi

#WPE_R4_COMMIT
WPE_CDMI_ADAPTER_IMPL = "${@get_cdmi_adapter(d)}"
#WPE_CDMI_ADAPTER_IMPL = "opencdm_gst"

PACKAGECONFIG ?= "opencdm ${WPE_CDMI_ADAPTER_IMPL}"

# OCDM
PACKAGECONFIG[opencdm]          = "-DCDMI=ON,-DCDMI=OFF,"
PACKAGECONFIG[opencdm_gst]      = '-DCDMI_ADAPTER_IMPLEMENTATION="gstreamer",,gstreamer1.0'
PACKAGECONFIG[opencdmi_prnx_svp]= '-DCDMI_BCM_NEXUS_SVP=ON -DCDMI_ADAPTER_IMPLEMENTATION="broadcom-svp",,'
PACKAGECONFIG[opencdmi_brcm_svp]= '-DCDMI_BCM_NEXUS_SVP=ON -DCDMI_ADAPTER_IMPLEMENTATION="broadcom-svp",,gstreamer-plugins-soc'
PACKAGECONFIG[opencdmi_comcast_svp]= '-DCDMI_BCM_NEXUS_SVP=ON -DCDMI_ADAPTER_IMPLEMENTATION="broadcom-svp-secbuf",,'
PACKAGECONFIG[opencdmi_rdk_svp]= '-DCDMI_ADAPTER_IMPLEMENTATION="rdk",,'
