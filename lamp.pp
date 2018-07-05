exec { "apt-update":
command => "/usr/bin/apt-get update"
}

package { "apache2":
require => Exec["apt-update"],
ensure => installed,
}

service { "apache2":
ensure => running,
}

package { "mysql-server":
require => Exec["apt-update"],
ensure => installed,
}

package { "maven":
require => Exec["apt-update"],
ensure => installed,
}

case $::operatingsystem {
    ubuntu: {
      include apt

      apt::ppa { 'ppa:openjdk-r/ppa':
        ensure => present,
      }

      exec { 'apt-update':
        command => '/usr/bin/apt-get update',
        require => [
          Apt::Ppa['ppa:openjdk-r/ppa']
        ],
      }

      package { 'openjdk-8-jdk':
        require  => [
          Exec['apt-update'],
          Apt::Ppa['ppa:openjdk-r/ppa'],
        ],
      }
    }

    default: {
      notice "Unsupported operatingsystem ${::operatingsystem}"
    }
  }
