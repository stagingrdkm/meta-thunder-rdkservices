SRCREV_FORMAT="wpeframework-clientlibraries"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

#replaced by 0003_MediaType_name_changed.patch (upstream patch) and then later by 0001-OCDM-enhancement-for-ocdm-adapter.patch (upstream patch)
#SRC_URI += "file://0008-OMWAPPI-911-Fix-for-conflicting-declaration-of-enum-MediaType.patch"
# that patch updated, some chunks of that patch are applied by 0001-OCDM-enhancement-for-ocdm-adapter.patch (upstream patch)
SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-to-wpeframework-clientlibraries-rdknext.patch"
SRC_URI += "file://0009-OMWAPPI-912-CBCS-Support-passing-Encryption-Scheme-and-Pattern-to-Decrypt.patch"
SRC_URI += "file://0010-OMWAPPI-912-Switch-to-opencdm_session_decrypt_v2.patch"
SRC_URI += "file://0001-OMWAPPI-912-Fix-artifacts-when-playing-CBCS-content-v2.patch"

SRC_URI += "file://0106-ARRISAPP-713-disable-ocmd-assert-in-Reconect.patch"
SRC_URI += "file://0107-ONEM-31898-opencdm_dispose-only-destroy-accessor.patch"

SRC_URI += "file://0108-ONEM-36378-OnBindLicense.patch"
SRC_URI += "file://0109-ONEM-39202-New-store-clean-up-API.patch"
SRC_URI += "file://0110-ONEM-38588-Improve-PR-lifecycle.patch"
SRC_URI += "file://0111-ONEM-38885-multi-decryption.patch"
SRC_URI += "file://0112-ONEM-38627-Extend-Error-Codes.patch"
SRC_URI += "file://0113-ONEM-41322-Retrieve-license-URL-alongside-challenge.patch"
SRC_URI += "file://0114-ONEM-41323-Use-extended-error-codes.patch"
SRC_URI += "file://0115-ONEM-41325-Bind-callback-extensions.patch"
SRC_URI += "file://0116-ONEM-41577-backport-from-rdkcentral.patch"
SRC_URI += "file://0117-ONEM-41577-OCDM-session-creation-modified.patch"
SRC_URI += "file://0118-ONEM-40864-Deinitialize-DRM-on-client-crash.patch"
SRC_URI += "file://0119-ARRISEOS-48273-Decryption-of-small-chunks-corrected.patch"
SRC_URI += "file://0120-ONEM-42412-Implement-opencdm_gstreamer_session_decrypt_buffer.patch"
SRC_URI += "file://0121-ONEM-42508-New-version-of-opencdm_system_get_version.patch"
SRC_URI += "file://0122-ARRISEOS-48136-WPE-Process-crash-ocdm-stop-fixed.patch"
SRC_URI += "file://0123-ONEM-42848-Update-OCDM-license-bind-callback.patch"
SRC_URI += "file://0124-ARRISEOS-48107-Remove-API-Implementation.patch"

EXTRA_OECMAKE_append = " -DOCDM_IMPLEMENTATION_PATH=adapter/broadcom-svp "

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON -DSETUP_TEST_KEY', '', d)}"

LDFLAGS_append=" -Wl,--no-undefined"
