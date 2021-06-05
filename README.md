# EnderLibs
EnderLibs is a Spigot plugin development library, designed to make life easier and stop yourself from putting a figurative bullet in your head during plugin development. Below, find instructions for installation & usage.

# Installation for Development

EnderLibs is a standalone plugin, so you will need to install it on any servers that have plugins which depend on it, and specify it as a dependency in your plugin.yml like this:

`depend: [EnderLibs]`

To get the jar, download it from the releases tab here on [GitHub](https://github.com/EnderGamingFilms/EnderLibs/releases)

### Maven:

```
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```

```
<dependency>
	<groupId>com.github.EnderGamingFilms</groupId>
	<artifactId>EnderLibs</artifactId>
	<version>LATEST</version>
</dependency>
```