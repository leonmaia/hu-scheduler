sudo apt-get install virtualbox
sudo apt-get install vagrant
sudo apt-get install virtualbox-dkms
vagrant up --provision
vagrant ssh -c "cd /vagrant/ember-front-end && nohup ember server &"
echo Ember: Running at :4200
vagrant ssh -c "cd /vagrant/hotel-urbano-api && java_opts='-Xms128M -Xmx512M' ./activator run"
echo Play: Running at :9000