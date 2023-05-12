SUMMARY = "RDK services LgiTextToSpeech plugin"

PROVIDES += "rdkservice-texttospeech"
RPROVIDES_${PN} += "rdkservice-texttospeech"

# plugin directory in rdkservices repository
PLUGINDIR="LgiTextToSpeech"

require rdkservices-common/common_plugin.inc
OECMAKE_TARGET_COMPILE = "WPEFrameworkTextToSpeech"

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}= "${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE  += "-DPLUGIN_LGITEXTTOSPEECH=ON "

DEPENDS += "glib-2.0 tts"
