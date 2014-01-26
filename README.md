# `net.loadbang.osc` [![Build Status](https://secure.travis-ci.org/cassiel/net.loadbang.osc.png)](http://travis-ci.org/cassiel/net.loadbang.osc) [![Dependency Status](https://www.versioneye.com/user/projects/52e4ddccec1375b57600002a/badge.png)](https://www.versioneye.com/user/projects/52e4ddccec1375b57600002a)

This is a simple [OSC][osc] library used by our [MaxMSP][max] packages,
and elsewhere (such as directly from Clojure). It is missing some
features (specifically, wildcard matching of paths, and timestamping),
but it does implement OSC-over-TCP as well as the more usual
OSC-over-UDP.

For a Clojure-friendly wrapper to this package, see the lower-level parts of
[`monome-fu`](https://github.com/cassiel/monome-fu).

`net.loadbang.osc` and its dependencies are available in the central Maven repo:

- leiningen:

        [net.loadbang/net.loadbang.osc "1.6.1"]

- maven:

        <dependency>
          <groupId>net.loadbang</groupId>
          <artifactId>net.loadbang.osc</artifactId>
          <version>1.6.1</version>
        </dependency>

## Documentation

The JavaDocs can be generated from Maven by

        mvn javadoc:javadoc

The documentation is written to `target/site/apidocs`.

## License

Distributed under the [GNU General Public License][gpl].

Copyright (C) 2013 Nick Rothwell.

[max]: http://cycling74.com/products/max/
[lib]: https://github.com/cassiel/net.loadbang.lib
[osc]: http://opensoundcontrol.org/
[gpl]: http://www.gnu.org/copyleft/gpl.html
