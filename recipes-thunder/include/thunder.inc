# WPE Framework common
LICENSE = "Apache-2.0"
HOMEPAGE = "https://github.com/rdkcentral/Thunder"

PACKAGECONFIG ??= ""
PACKAGECONFIG[debug]            = "-DCMAKE_BUILD_TYPE=Debug,-DCMAKE_BUILD_TYPE=Release,"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"

