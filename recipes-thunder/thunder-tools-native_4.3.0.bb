SUMMARY = "Host/Native tooling for the Web Platform for Embedded Framework"
require include/thunder.inc
require include/thunder_${PV}.inc

SRC_URI_remove = "git://github.com/rdkcentral/Thunder.git;protocol=https;branch=R4;name=thunder"

SRC_URI = "git://github.com/tomasz-karczewski-red/ThunderTools.git;protocol=https;branch=R4X;name=thundertools"
SRCREV_thundertools="8b13b59d0c773e3457c1c82580bc2d0309566cc3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c3349dc67b2f8c28fc99b300eb39e3cc"

SRC_URI += "file://0001-JsonGenerator-Fix-buffer-passing-when-buffer-is-outp.patch"

PROVIDES += "wpeframework-tools-native"

inherit cmake pkgconfig native python3native

RDEPENDS_${PN} = "python3"


DEPENDS = "\
    python3-native \
    python3-jsonref-native \
"

DEPENDS_append_daisy = " python3-enum34-native"


FILES_${PN} += "${datadir}/*/Modules/*.cmake"
FILES_${PN} += "${prefix}/include/WPEFramework/Modules/*.cmake"

do_generate_toolchain_file_append_daisy() {
  sed -i 's/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM ONLY/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM BOTH/g' ${WORKDIR}/toolchain.cmake
}
