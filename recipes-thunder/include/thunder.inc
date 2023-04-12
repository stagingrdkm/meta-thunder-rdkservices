# WPE Framework common
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=85bcfede74b96d9a58c6ea5d4b607e58"
HOMEPAGE = "https://github.com/rdkcentral/Thunder"

SRC_URI = "git://github.com/rdkcentral/Thunder.git;protocol=https;branch=R4;name=thunder"

# R4.1 tag version
SRCREV_thunder = "65bff2a6c5599ccbeb01d77a16417ad9d09df918"
PACKAGECONFIG ??= ""
PACKAGECONFIG[debug]            = "-DCMAKE_BUILD_TYPE=Debug,-DCMAKE_BUILD_TYPE=Release,"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"

