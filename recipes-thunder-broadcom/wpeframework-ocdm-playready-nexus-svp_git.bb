SUMMARY = "WPE Framework OpenCDMi module for PlayReady Nexus SVP/SAGE dedicated"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=363ee002438e52dac11707343db81c4e"
PROVIDES = "virtual/ocdmserver-PR-backend"

require include/wpeframework-plugins.inc

DEPENDS += " broadcom-refsw wpeframework"

SRC_URI = "${CMF_GIT_ROOT}/soc/broadcom/components/rdkcentral/OCDM-Playready-Nexus-SVP;protocol=${CMF_GIT_PROTOCOL};branch=master"
# Revision hash of R1 release
SRCREV = "29a1ce2fce8d4feab23a38d762decbdc7968201a"

FILES_${PN} = "${datadir}/WPEFramework/OCDM/*.drm"

