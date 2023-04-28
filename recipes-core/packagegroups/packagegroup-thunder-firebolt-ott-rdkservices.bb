SUMMARY = "additional firebolt services used by the TOP tier Premium OTT apps, indentified during Netflix6.x, Cobalt23, AVPKv5, Disney+3 porting"
inherit packagegroup

PACKAGES = "packagegroup-thunder-firebolt-ott-rdkservices"

# now tuned to/setup like in ApolloV1+ distro
RDEPENDS_${PN} += "rdkservice-deviceidentification"
RDEPENDS_${PN} += "rdkservice-displayinfo"
RDEPENDS_${PN} += "rdkservice-playerinfo"
RDEPENDS_${PN} += "rdkservice-displaysettings"
RDEPENDS_${PN} += "rdkservice-hdcpprofile"
RDEPENDS_${PN} += "rdkservice-hdmicec"
RDEPENDS_${PN} += "rdkservice-wifimanager"
RDEPENDS_${PN} += "rdkservice-xcast"
RDEPENDS_${PN} += "rdkservice-network"
RDEPENDS_${PN} += "rdkservice-system"

# removed from LGI/ApolloV1+ distro
# require ttsproxy to compile, removed with that chage: https://gerrit.onemw.net/c/onemw-src/+/107139/2/rdk/tts/CMakeLists.txt
#RDEPENDS_${PN} += "rdkservice-texttospeech"
