SRCREV_FORMAT="wpeframework-clientlibraries"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

# Existing patches from meta-rdk-video layer were either removed or replaced with their
# equivalents with "-rdknext" suffix that were rebased against newer ClientLibraries version R2-v1.12.
# Removed patches add changes to adapters that are not currently used by LGI
# https://wikiprojects.upc.biz/display/CTOM/OCDM+adapter+CBCS+support#OCDMadapterCBCSsupport-Adapterimplementations 
SRC_URI_remove = "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-to-wpeframework-clientlibraries.patch"
SRC_URI_remove = "file://0008-added-opencdm_gstreamer_transform_caps-method.patch"
SRC_URI_remove = "file://0009-DELIA-51340.patch"

# we have our alternative here (different name)
#           file://0003_MediaType_name_changed.patch 
#           file://0001_gstreamer_session_decrypt_ex_with_caps.patch - we have it provided elsewhere
SRC_URI_remove = "file://0003_MediaType_name_changed.patch"
SRC_URI_remove = "file://0001_gstreamer_session_decrypt_ex_with_caps.patch"

SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-to-wpeframework-clientlibraries-rdknext.patch"

# CBCS-related patches
SRC_URI += "file://0009-OMWAPPI-912-CBCS-Support-passing-Encryption-Scheme-and-Pattern-to-Decrypt.patch"
SRC_URI += "file://0008-OMWAPPI-911-Fix-for-conflicting-declaration-of-enum-MediaType.patch"
SRC_URI += "file://0010-OMWAPPI-912-Switch-to-opencdm_session_decrypt_v2.patch"
SRC_URI += "file://0001-OMWAPPI-912-Fix-artifacts-when-playing-CBCS-content.patch"

# not aplicable here, but should go to wpeframework-interfaces
# SRC_URI += "file://0007-ONEM-24354-Clear-content-of-buffers-on-deinit.patch"

SRC_URI += "file://0104-OMWAPPI-1314-Extend-ThunderClientLibraries-metrics-API.patch"

EXTRA_OECMAKE_append = " -DOCDM_IMPLEMENTATION_PATH=adapter/broadcom-svp "

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON -DSETUP_TEST_KEY', '', d)}"

LDFLAGS_append=" -Wl,--no-undefined"
