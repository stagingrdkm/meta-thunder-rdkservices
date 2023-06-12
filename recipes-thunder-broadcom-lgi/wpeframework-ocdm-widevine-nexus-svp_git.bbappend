DEPENDS += "wpeframework-tools-native wpeframework-interfaces libodherr"
RDEPENDS_{$PN} += "libodherr"

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"
FILESEXTRAPATHS_append := ":${THISDIR}/cmake"

# SWRDKV-2413/SWRDKV-2773, in dunfell build, each module has specific sysroot.
# Since in case of sage dtcp build, libcmndrm_tl.so is provided by dtcp, below are needed.
DEPENDS += "${@oe.utils.conditional('WITHOUT_DTCP', 's', 'dtcp','', d)}"
RDEPENDS_${PN} += "${@oe.utils.conditional('WITHOUT_DTCP', 's', 'dtcp','', d)}"

S = "${WORKDIR}/OCDM-Widevine-Nexus-SVP"
LIC_FILES_CHKSUM = "file://LICENSES.TXT;md5=3d570228a530da5cf9f51fe9183bc64b"

EXTRA_OECMAKE += "-DEXPORT_SYMBOLS=ON "
EXTRA_OECMAKE += "${@oe.utils.conditional('CONF_ONEMW_WIDEVINE_VERSION', 'CENC_v16', '-DWIDEVINE_VERSION=16 ', '', d)}"

# to link to security libraries
ASNEEDED = ""

SRC_URI+="file://SWRDKV-1523.wpeframework-ocdm-widevine-nexus-svp.patch;striplevel=1"
SRC_URI+="file://SWRDKV-1736.wpeframework-ocdm-widevine-nexus-svp.light_constructor.patch;striplevel=1"
SRC_URI+="file://SWRDKV-1819.wpeframework-ocdm-widevine-nexus-svp.cmake.patch;striplevel=1"
#SRC_URI+="file://SWRDKV-1819.wpeframework-ocdm-widevine-nexus-svp.cert.patch;striplevel=1"
SRC_URI+="file://SWRDKV-1819.wpeframework-ocdm-widevine-nexus-svp.null_license_url.patch;striplevel=1"
SRC_URI+="file://SWRDKV-1904.wpeframework-ocdm-widevine-nexus-svp.avoiding_dealock_keystatus_callback.patch;striplevel=1"
SRC_URI+="file://SWRDKV-2168.wpeframework-ocdm-widevine-nexus-svp.cenc_version_14.patch;striplevel=1"
SRC_URI+="file://SWRDKV-2275.wpeframework-ocdm-widevine-nexus-svp.standy.ack.patch;striplevel=1"
SRC_URI+="file://SWRDKV-2588.wpeframework-ocdm-widevine-nexus-svp_widevine_v15.patch;striplevel=1"
SRC_URI+="file://SWRDKV-2978_MODULE_NAME_WideVine.patch;striplevel=1"
SRC_URI+="file://0001-fix-build-and-library-loading.patch;striplevel=1"
SRC_URI+="file://0001-improve-widevine-logging.patch;striplevel=1"
SRC_URI+="file://0001-Use-reverse-proxy-for-provisioning.patch;striplevel=1"
SRC_URI+="file://0002-ONEM-25410-error-reporting.patch"
SRC_URI+="file://OMWAPPI-930-Add-Widevine-v16-support-to-OCDM.patch;striplevel=1"
SRC_URI+="file://OMWAPPI-914-widevine_cbcs_support.patch"
SRC_URI+="file://OMWAPPI-914-widevine_pass_subsample_information.patch"
SRC_URI += "file://OMWAPPI-1634-Implementation-of-IMediaSystemMetrics.patch"
SRC_URI += "file://OMWAPPI-1492-Implement-Widewine-settings-in-WideVine.patch"

SRC_URI += "file://FindNEXUS.cmake;subdir=OCDM-Widevine-Nexus-SVP/cmake"
SRC_URI += "file://FindNXCLIENT.cmake;subdir=OCDM-Widevine-Nexus-SVP/cmake"

