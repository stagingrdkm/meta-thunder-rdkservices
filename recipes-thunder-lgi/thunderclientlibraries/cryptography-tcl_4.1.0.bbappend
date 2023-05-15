SRCREV_FORMAT="wpeframework-clientlibraries"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://0005-ONEM-23352-Multiple-EXTERNAL-introduced-for-external.patch"
SRC_URI += "file://0101-OMWAPPI-1119-cobalt-vault.patch"
SRC_URI += "file://0102-OMWAPPI-1119-cobalt-vault-implementation.patch;patchdir=Source/cryptography/implementation/Icrypto-nexus"

SRC_URI += "file://0103-OMWAPPI-1119-cobalt-vault-tests.patch"

SRC_URI += "file://0105-iCrypto-nexus-platform-vault.patch"
SRC_URI += "file://0105-iCrypto-nexus-platform-vault-tests.patch"

# other options OpenSSL, SecApi, Thunder
CRYPTOGRAPHY_IMPLEMENTATION="Icrypto-nexus"

EXTRA_OECMAKE_append = " -DCRYPTOGRAPHY_IMPLEMENTATION_PATH=${CRYPTOGRAPHY_IMPLEMENTATION}"
EXTRA_OECMAKE_append = "${@bb.utils.contains('MACHINE_FEATURES', 'debug', ' -DBUILD_CRYPTOGRAPHY_TESTS=ON', '', d)}"
TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON -DSETUP_TEST_KEY', '', d)}"

DEPENDS_remove = "virtual/secapi"
DEPENDS_remove = "secapi-netflix"

LDFLAGS_append=" -Wl,--no-undefined"
