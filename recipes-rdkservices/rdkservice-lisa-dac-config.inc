ASMS_URL ?= ""
LISA_DAC_CONFIG_PLATFORM ?= ""
LISA_DAC_CONFIG_FIRMWARE ?= ""

python do_inject_dac_config() {
    import json


    for root, dirs, files in os.walk(d.getVar('D', True)):
        if 'LISA.json' in files:
            file_path = os.path.join(root, 'LISA.json')
            print(f"Found LISA config at: {file_path}")

            with open(file_path, 'r') as f:
                json_data = json.load(f)

            variables_mapping = {
                'LISA_DAC_CONFIG_PLATFORM': 'dacBundlePlatformNameOverride',
                'LISA_DAC_CONFIG_FIRMWARE': 'dacBundleFirmwareCompatibilityKey',
                'ASMS_URL': 'asmsUrl'
            }

            for yocto_var, json_key in variables_mapping.items():
                value = d.getVar(yocto_var, True).strip()
                if value:
                    json_data['configuration'][json_key] = value

            with open(file_path, 'w') as f:
                json.dump(json_data, f, indent=2)
}

addtask inject_dac_config after do_install before do_package
