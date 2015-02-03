echo Installing VirtualBox
sudo apt-get install virtualbox
sudo apt-get install virtualbox-dkms
echo VirtualBox: Ok!

echo Installing Vagrant
sudo apt-get install vagrant
echo Vagrant: Ok!

echo Installing Ansible
sudo apt-get install software-properties-common
sudo apt-add-repository ppa:ansible/ansible
sudo apt-get update
sudo apt-get install ansible
echo Ansible: Ok!

echo Provisioning machine with Vagrant
vagrant up --provision
echo Vagrant Provision: Ok!

echo Ember: Start!
vagrant ssh -c "cd /vagrant/ember-front-end && nohup ember server &"
echo Ember: Running at :4200

echo Play: Start!
vagrant ssh -c "cd /vagrant/hotel-urbano-api && java_opts='-Xms128M -Xmx512M' ./activator run"
echo Play: Running at :9000