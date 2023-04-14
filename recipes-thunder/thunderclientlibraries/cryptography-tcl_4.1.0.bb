SUMMARY = "WPEFramework cryptography client libraries"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder_clientlibraries_${PV}.inc

SRC_URI += "file://0004-Cipher-CipherNetflix-methods-return-type-changes.patch \
            file://R4.1_Trace_enabled_error_wpeframework_clientlibraries.patch \
           "
#Cryptography library 
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'enable_icrypto_openssl','openssl', 'virtual/secapi', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'enable_icrypto_openssl','', 'secapi-netflix', d)}"
DEPENDS +=  "${@bb.utils.contains('DISTRO_FEATURES', 'enable_icrypto_openssl',"", bb.utils.contains('DISTRO_FEATURES', 'netflix_cryptanium', 'virtual/secapi-crypto', "", d), d)}"
CRYPTOGRAPHY_IMPLEMENTATION = "${@bb.utils.contains('DISTRO_FEATURES', 'enable_icrypto_openssl','OpenSSL', 'SecApi', d)}"

PACKAGECONFIG ?= "cryptography"
PACKAGECONFIG[cryptography]     = "-DCRYPTOGRAPHY=ON, -DCRYPTOGRAPHY=OFF,"

EXTRA_OECMAKE += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'netflix_cryptanium', '-DSECAPI_ENGINE_CRYPTANIUM=1', '', d)} \
    -DCRYPTOGRAPHY_IMPLEMENTATION=${CRYPTOGRAPHY_IMPLEMENTATION}\
    ${@bb.utils.contains('DISTRO_FEATURES', 'enable_firebolt_compliance_tdk', '-DBUILD_CRYPTOGRAPHY_TESTS=ON', '', d)} \
"

CXXFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'netflix_cryptanium', " -I${STAGING_INCDIR}/secapi-crypto/sec_api/headers", "", d)}"
