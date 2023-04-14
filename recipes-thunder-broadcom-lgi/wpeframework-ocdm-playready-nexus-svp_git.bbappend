DEPENDS += "wpeframework-tools-native wpeframework-interfaces libodherr"
RDEPENDS_{$PN} += "libodherr"

LIC_FILES_CHKSUM = "file://LICENSES.TXT;md5=3d570228a530da5cf9f51fe9183bc64b"

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"
FILESEXTRAPATHS_append := ":${THISDIR}/cmake"

TARGET_CXXFLAGS += "-DMODULE_NAME=OpenCDM"
TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON', '', d)}"

S = "${WORKDIR}/OCDM-Playready-Nexus-SVP"
SRC_URI += "file://0102-ONEM-18284-OCDM-Playready-Nexus-SVP-compilation.patch"
SRC_URI += "file://0103-Playready-initialize.patch"
SRC_URI += "file://0107-ONEM-20471-ocdm-respect-read-dir-storelocation-props.patch"
SRC_URI += "file://0110-ONEM-22976-Link-life-cycles-of-system-initialization.patch"
SRC_URI += "file://0111-ONEM-22976-Fix-memleaks-on-DeinitializeSystem.patch"
SRC_URI += "file://0112-ONEM-22983-Propagate-Playready-error-from-InitializeSystem.patch"
SRC_URI += "file://0113-ONEM-22977_Store_Cleanup_improvements.patch"
SRC_URI += "file://0114-ONEM-15903-error-reporting.patch"
SRC_URI += "file://0115-OMWAPPI-913-playready_cbcs_support.patch"
SRC_URI += "file://FindNEXUS.cmake;subdir=OCDM-Playready-Nexus-SVP/cmake"
SRC_URI += "file://FindNXCLIENT.cmake;subdir=OCDM-Playready-Nexus-SVP/cmake"
SRC_URI += "${@oe.utils.version_less_or_equal('URSR_VERSION', '20', '', \
           'file://SWRDKV-2593.wpeframework-ocdm-playready-nexus-svp_updating_for_ursr20.2.patch;striplevel=1', d)}"

SRC_URI += "file://0116-OMWAPPI-1316-Implementation-of-IMediaSystemMetrics.patch"
SRC_URI += "file://0117-ONEM-29883-fix-asserts.patch"
