FILESEXTRAPATHS_prepend := "${THISDIR}/thunder-ui:"

SRC_URI += "file://0001-ONEM-23840-bundle-js-provided-in-gz-file.patch"

do_install_append() {
    rm -rf ${D}${datadir}/WPEFramework/Controller/UI/css
    rm -rf ${D}${datadir}/WPEFramework/Controller/UI/js
    rm -f ${D}${datadir}/WPEFramework/Controller/UI/main.js
    gzip -9 ${D}${datadir}/WPEFramework/Controller/UI/bundle.js
}
