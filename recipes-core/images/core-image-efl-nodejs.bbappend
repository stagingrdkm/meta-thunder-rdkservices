IMAGE_INSTALL += "packagegroup-thunder-base-rdkservices"
IMAGE_INSTALL += "packagegroup-thunder-firebolt-rdkservices"
IMAGE_INSTALL += "packagegroup-thunder-firebolt-ott-rdkservices"
IMAGE_INSTALL += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'packagegroup-thunder-dac-rdkservices', '', d)}"
