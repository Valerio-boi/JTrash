module JTrash {
	exports jtrash.controller;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	opens jtrash.model to javafx.base;
}