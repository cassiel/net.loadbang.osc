# net.loadbang.osc

This is a simple [OSC][osc] library used by our [MaxMSP][max]
packages. It is missing some features (specifically, wildcard matching
of paths, and timestamping), but it does implement OSC-over-TCP as
well as the more usual OSC-over-UDP.

The prebuilt JAR files are in the sub-directory `distribution`, or the
library can be built from the enclosed sources using Maven. (For the
Maven build, clone and build [net.loadbang.lib][lib] first, since our
libraries are not yet in a central repository.)

See the README for [net.loadbang.lib][lib] for installation details.

## Documentation

The JavaDocs can be generated from Maven by

	mvn javadoc:javadoc

The documentation is written to `target/site/apidocs`.

## License

Distributed under the [GNU General Public License][gpl].

Copyright (C) 2011 Nick Rothwell.

[max]: http://cycling74.com/products/max/
[lib]: https://github.com/cassiel/net.loadbang.lib
[osc]: http://opensoundcontrol.org/
[gpl]: http://www.gnu.org/copyleft/gpl.html
