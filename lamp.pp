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


package { "default-jre":
require => Exec["apt-update"],
ensure => installed,
}

package { "default-jdk":
require => Exec["apt-update"],
ensure => installed,
}

package { "maven":
require => Exec["apt-update"],
ensure => installed,
}
