# WPE Framework common
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=85bcfede74b96d9a58c6ea5d4b607e58"
HOMEPAGE = "https://github.com/rdkcentral/Thunder"

THUNDER_RELEASE_TAG_NAME = "R2-v1.9"

SRC_URI = "git://github.com/rdkcentral/Thunder.git;protocol=https;branch=R4;name=thunder"

# R2-v1.9 tag revision
SRCREV_thunder = "f9aa9e8b4d15f8998fc1badd8bf3b77185b5e450"
# R4.1 tag version
SRCREV_thunder = "65bff2a6c5599ccbeb01d77a16417ad9d09df918"
PACKAGECONFIG ??= ""
PACKAGECONFIG[debug]            = "-DCMAKE_BUILD_TYPE=Debug,-DCMAKE_BUILD_TYPE=Release,"

PV = "${RDK_RELEASE}+git${SRCPV}"
S = "${WORKDIR}/git"

inherit cmake pkgconfig

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"

