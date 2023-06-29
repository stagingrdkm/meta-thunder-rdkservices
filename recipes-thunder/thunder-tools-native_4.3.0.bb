SUMMARY = "Host/Native tooling for the Web Platform for Embedded Framework"
require include/thunder.inc
require include/thunder_${PV}.inc

SRC_URI_remove = "git://github.com/rdkcentral/Thunder.git;protocol=https;branch=R4;name=thunder"

SRC_URI = "git://github.com/rdkcentral/ThunderTools.git;protocol=https;branch=R4;name=thundertools"
SRCREV_thundertools="13a2c71dec3cfab814a41b6a86dd8ea09ee17698"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c3349dc67b2f8c28fc99b300eb39e3cc"

#file://0002-StubGenerator.patch already fixed with f1c0b98878bd236c7345ffd0d11b38fd880c8d60
#file://0005-RDKTV-16258-lambda_crash_error.patch this part of code was rewritten with 32127c8cd5a840fb7a640875fb2835a5eb0e489d

PROVIDES += "wpeframework-tools-native"

inherit cmake pkgconfig native python3native

RDEPENDS_${PN} = "python3"

DEPENDS = "\
    python3-native \
    python3-jsonref-native \
"

DEPENDS_append_daisy = " python3-enum34-native"

#OECMAKE_SOURCEPATH = "${WORKDIR}/git/Tools"

FILES_${PN} += "${datadir}/*/Modules/*.cmake"
FILES_${PN} += "${prefix}/include/WPEFramework/Modules/*.cmake"

#EXTRA_OECMAKE_append_daisy = " ${OECMAKE_SOURCEPATH} "
do_generate_toolchain_file_append_daisy() {
  sed -i 's/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM ONLY/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM BOTH/g' ${WORKDIR}/toolchain.cmake
}
