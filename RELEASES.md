`-*- word-wrap: t; -*-`

## 1.6.1, 2013-10-28:

Dependency bump: `net.loadbang.picolib` `1.0.0`.

Java source and target locked to `1.5` in `pom.xml`.

## 1.6.0, 2013-07-14:

Now depends on `net.loadbang.picolib` (not `net.loadbang.lib`) so that we can purge out dependencies on the Max code. MXJ-related writers and proxies removed. (Do it in Clojure or Python, dude.)

## 1.5.0, 2013-02-26:

New method:

    MessageConsumer.consumeMessage(InetSocketAddress source, Date timestamp00, Message message);

This provides the source host and port. By default, the Receiver superclass provides this and calls the original two-argument version for backwards compatibility. To use the new version in other code, the original probably has to be provided as well (certainly to instance a receiver in Java; Clojure might be more forgiving).

## 1.4.2, 2013-02-16:

Dependency clean-up.
