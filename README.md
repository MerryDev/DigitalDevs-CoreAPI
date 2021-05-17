# DigitalDevs.de CoreAPI

___

![Build](https://github.com/MerryDev/DigitalDevs-CoreAPI/actions/workflows/maven.yml/badge.svg)
[![Generic badge](https://img.shields.io/badge/version-1.0-informational.svg)](https://shields.io/)

## About

Die CoreAPI von DigitalDevs.de ist in erster Linie dafür entwickelt worden, um wiederkehrende Datenstrukturen, Inhalte
und Prozesse zentral verwalten zu können.

## Features

Der aktuelle Build der API umfasst folgende Inhalte und Funktionen:

* Factory-Patterns zum einfachen Erstellen von Items, texturierten Spielerköpfen, Tränken und farbiger Lederrüstung
* Eine abstrakte Config zur einfachen Erstellung eines Grundgerüstes einer jeden Config-Datei
* Reflections (Field- & ClassAccessor) um während des Runtimes Objekte modifizieren zu können und auf geschützte Klassen
  zugreifen zu können
* Globale und persönliche Scoreboards auf Basis von Funktionen und Methoden
* Neue Inventare:
    * Komplett mit einem Item ausgefüllte Inventare
    * Inventare mit einer Umrandung aus einem Item
* Lizenzsystem für Plugins auf Basis der Bungeecord Serversoftware und für auf nicht Bungeecord basierende Plugins
