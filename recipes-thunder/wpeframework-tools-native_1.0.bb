SUMMARY = "Host/Native tooling for the Web Platform for Embedded Framework"
require include/thunder.inc
SRC_URI_append = " file://0002-StubGenerator.patch"


inherit cmake pkgconfig native python3native

DEPENDS = "\
    python3-native \
    python3-jsonref-native \
"

DEPENDS_append_daisy = " python3-enum34-native"

OECMAKE_SOURCEPATH = "${WORKDIR}/git/Tools"

FILES_${PN} += "${datadir}/*/Modules/*.cmake"

EXTRA_OECMAKE_append_daisy = " ${OECMAKE_SOURCEPATH} "
do_generate_toolchain_file_append_daisy() {
  sed -i 's/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM ONLY/CMAKE_FIND_ROOT_PATH_MODE_PROGRAM BOTH/g' ${WORKDIR}/toolchain.cmake
}
