require ../../include/thunder.inc
LIC_FILES_CHKSUM = "file://LICENSE;md5=847677038847363222ffb66cfa6406c2"
FILESEXTRAPATHS_prepend = "${THISDIR}/thunder-clientlibraries-${PV}:"

SRC_URI = "git://github.com/rdkcentral/ThunderClientLibraries.git;protocol=git;branch=R4;name=wpeframework-clientlibraries \
           file://Library-version-Matched-With-Release-ClientLibs.patch \
          "
# R4.3
SRCREV_wpeframework-clientlibraries = "50a850991d6e6208bbad7f0fa189f8c2c04429bf"

DEPENDS = " \
    wpeframework-interfaces \
    wpeframework-tools-native \
"

RDEPENDS_${PN}_append_dunfell += " wpeframework"

EXTRA_OECMAKE += " \
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_REFERENCE=${SRCREV_wpeframework-clientlibraries} \
    -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python3-native/python3 \
"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${datadir}/WPEFramework/* ${PKG_CONFIG_DIR}/*.pc"
FILES_${PN}-dev += "${libdir}/cmake/*"

INSANE_SKIP_${PN} += "dev-so"
INSANE_SKIP_${PN}-dbg += "dev-so"

# Avoid settings ADNEEDED in LDFLAGS as this can cause the libcompositor.so to drop linking to libEGL/libGLES
# which might not be needed at first glance but will cause problems higher up in the change, there for lets drop -Wl,--as-needed
# some distros, like POKY (morty) enable --as-needed by default (e.g. https://git.yoctoproject.org/cgit/cgit.cgi/poky/tree/meta/conf/distro/include/as-needed.inc?h=morty)
ASNEEDED = ""
