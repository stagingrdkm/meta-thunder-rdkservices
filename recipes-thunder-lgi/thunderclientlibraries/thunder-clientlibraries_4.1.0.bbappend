RDEPENDS_${PN}_remove = "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', '', 'cryptography-tcl', d)}"

deltask do_copy_brcmexternalsrc

