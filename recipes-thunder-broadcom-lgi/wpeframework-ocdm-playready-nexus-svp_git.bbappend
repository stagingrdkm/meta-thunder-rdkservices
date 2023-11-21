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
SRC_URI += "file://0116-OMWAPPI-1405-playready-cbcs_support.patch"
SRC_URI += "file://SWRDKV-3799.wpeframework-ocdm-playready-nexus-svp_secure_copying_input.patch"
SRC_URI += "file://FindNEXUS.cmake;subdir=OCDM-Playready-Nexus-SVP/cmake"
SRC_URI += "file://FindNXCLIENT.cmake;subdir=OCDM-Playready-Nexus-SVP/cmake"
SRC_URI += "file://SWRDKV-2593.wpeframework-ocdm-playready-nexus-svp_updating_for_ursr20.2.patch;striplevel=1"

SRC_URI += "file://0116-OMWAPPI-1634-Implementation-of-IMediaSystemMetrics.patch"
SRC_URI += "file://0117-ONEM-29883-fix-asserts.patch"
SRC_URI += "file://0118-ARRISAPP-270-NTS-ERROR-REPORTING-TC1-failed.patch"
SRC_URI += "file://0119-ARRISAPP-410-pass-keyHistoryFileName-to-playready-initialization.patch"
SRC_URI += "file://0120-ARRISAPP-594-NEXUS_MemoryBlock_DestroyToken-on-decry.patch"

SRC_URI += "file://0121-ONEM-32873-OCDM-CBCS-Playready-implementation-on-eos.patch"
