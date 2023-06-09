# WPE Power plugin is able to interface with the embedded system's power capabilities, such as AON blocks and suspend to ram

WPE_POWER_AUTOSTART ?= "true"
WPE_POWER_GPIOPIN ?= ""
WPE_POWER_GPIOTYPE ?= ""
WPE_POWER_DEP = "${@bb.utils.contains('PREFERRED_PROVIDER_virtual/egl', 'broadcom-refsw', 'broadcom-refsw', bb.utils.contains('PREFERRED_PROVIDER_virtual/egl', 'broadcom-ursr', 'broadcom-refsw', '', d), d)}"

# ----------------------------------------------------------------------------

PACKAGECONFIG[power] = "-DPLUGIN_POWER=ON -DPLUGIN_POWER_AUTOSTART=${WPE_POWER_AUTOSTART} \
    					-DPLUGIN_POWER_GPIOPIN=${WPE_POWER_GPIOPIN} \
    					-DPLUGIN_POWER_GPIOTYPE=${WPE_POWER_GPIOTYPE} \
    					,-DPLUGIN_POWER=OFF,${WPE_POWER_DEP}"

# ----------------------------------------------------------------------------