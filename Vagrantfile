# ```
# TODO ->
#
# install jdk8
#
# mysql script:
# change password to root
# create db
# ```

# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|

  config.vm.provision :puppet do |puppet|
    current_dir = __dir__
    puppet.manifests_path = current_dir
    puppet.manifest_file = "lamp.pp"
  end

  config.vm.box = "ubuntu/trusty64"
  config.vm.network "forwarded_port", guest: 80, host: 8080

end
