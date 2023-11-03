# Common for WPE Framework plugins

S = "${WORKDIR}/git"

inherit cmake pkgconfig

TOOLCHAIN = "gcc"

EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"
EXTRA_OECMAKE += "-DTOOLS_SYSROOT=${STAGING_DIR_NATIVE}"
EXTRA_OECMAKE += "-DURSR_VERSION=${URSR_VERSION}"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/wpeframework/plugins/*.so"
