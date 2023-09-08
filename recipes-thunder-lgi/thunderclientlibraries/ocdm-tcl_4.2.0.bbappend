SRCREV_FORMAT="wpeframework-clientlibraries"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

#replaced by 0003_MediaType_name_changed.patch (upstream patch) and then later by 0001-OCDM-enhancement-for-ocdm-adapter.patch (upstream patch)
#SRC_URI += "file://0008-OMWAPPI-911-Fix-for-conflicting-declaration-of-enum-MediaType.patch"
# that patch updated, some chunks of that patch are applied by 0001-OCDM-enhancement-for-ocdm-adapter.patch (upstream patch)
SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-to-wpeframework-clientlibraries-rdknext.patch"
SRC_URI += "file://0009-OMWAPPI-912-CBCS-Support-passing-Encryption-Scheme-and-Pattern-to-Decrypt.patch"
SRC_URI += "file://0010-OMWAPPI-912-Switch-to-opencdm_session_decrypt_v2.patch"
SRC_URI += "file://0001-OMWAPPI-912-Fix-artifacts-when-playing-CBCS-content-v2.patch"

EXTRA_OECMAKE_append = " -DOCDM_IMPLEMENTATION_PATH=adapter/broadcom-svp "

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON -DSETUP_TEST_KEY', '', d)}"

LDFLAGS_append=" -Wl,--no-undefined"
