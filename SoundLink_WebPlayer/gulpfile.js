var gulp = require('gulp');
var gulpif = require('gulp-if');
var uglify = require('uglify-js');
var zip = require('gulp-zip');
var gutil = require('gulp-util');
var p = require('gulp-load-plugins')();
var cleanCSS = require('clean-css');
var less = require('gulp-less');
var LessAutoprefix = require('less-plugin-autoprefix');
var autoprefix = new LessAutoprefix({ browsers: ['last 2 versions'] });
var path = require('path');
var browserify = require('gulp-browserify');
var shell = require('gulp-shell');
var templateCache = require('gulp-angular-templatecache');

var vendorOutputStream;

var appName = "soundlink";
var isEnvProd = false;

function getEnv() {
	return "";
}

function envProd() {
	return isEnvProd;
}

/**
 * Concatenate and minify third-party JS.
 */
gulp.task('process-third-js', function () {
	var src = [
		'bower_components/angular/angular.js',
		'bower_components/angular-resource/angular-resource.js',
		'bower_components/angular-aria/angular-aria.js',
		'bower_components/angular-animate/angular-animate.js',
		'bower_components/angular-material/angular-material.js',
		'bower_components/angular-cookies/angular-cookies.js',
		'bower_components/angular-messages/angular-messages.js',
		'bower_components/angular-translate/angular-translate.js',
		'bower_components/angular-translate-loader-url/angular-translate-loader-url.js',
		'bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js',
		'bower_components/angular-ui-router/release/angular-ui-router.js',
		'bower_components/angular-websocket/angular-websocket.js',
		'bower_components/ng-file-upload/ng-file-upload.js',
		'bower_components/ng-file-upload-shim/ng-file-upload.js',
		'bower_components/angular-sanitize/angular-sanitize.js',
		'bower_components/moment/moment.js',
		'bower_components/lodash/dist/lodash.js',
		'bower_components/angular-moment/angular-moment.js',
		'bower_components/angular-bind-html-compile/angular-bind-html-compile.js',
	];

	vendorOutputStream = gulp.src(src)

		// Avoid in production
		.pipe(gulpif(!envProd(), p.concatSourcemap('third-party.js', {
			sourcesContent: true
		})));

	// Production environment
	return vendorOutputStream
		.pipe(gulpif(envProd(), p.concat('third-party.js')))
		.pipe(gulpif(envProd(),
			p.sizereport({
				gzip: true,
				total: false,
				minifier: function (contents, filepath) {
					return uglify.minify(contents, {
						fromString: true
					}).code;
				}
			})
		))
		.pipe(gulpif(envProd(), p.uglify()))

		.pipe(gulp.dest(appName + '/public/js'));
});

gulp.task('process-js', function () {
	var src = [appName + '/app/**/*.js'];

	return gulp.src(src)
		.pipe(p.iife({
			prependSemicolon: false,
			useStrict: false
		}))
		.pipe(p.angularInjector())
		.pipe(p.angularFilesort())

		// Avoid in production
		.pipe(gulpif(!envProd(), p.concatSourcemap('app.js', {
			sourcesContent: true
		})))

		// Production environment
		.pipe(gulpif(envProd(), p.concat('app.js')))
		.pipe(gulpif(envProd(), p.stripDebug()))
		.pipe(gulpif(envProd(),
			p.sizereport({
				gzip: true,
				total: true,
				minifier: function (contents, filepath) {
					return uglify.minify(contents, {
						fromString: true
					}).code;
				}
			})
		))
		.pipe(gulpif(envProd(), p.uglify()))
		.pipe(gulp.dest(appName + '/public/js'));
});

gulp.task('process-css', function () {
	var src = [
		appName + '/app/assets/less/**/*.less',
		'bower_components/material-design-icons/iconfont/material-icons.css',
		'bower_components/angular-material/angular-material.css'
	];

	return gulp.src(src)
		.pipe(less({
			plugins: [autoprefix]
		})).on('error', gutil.log)
		.pipe(p.concat('styles.css'))
		.pipe(gulpif(envProd(), p.sizereport({
			gzip: true,
			total: false,
			minifier: function (contents, filepath) {
				return new cleanCSS().minify(contents).styles;
			}
		})))
		.pipe(gulpif(envProd(), p.cleanCss()))
		.pipe(gulp.dest(appName + '/public/css'));
});

gulp.task('process-html', function () {
	return gulp.src(appName + '/app/**/*.html')
		.pipe(templateCache("app-html.js", {
			root: "app",
			module: "soundlink"
		}))
		.pipe(gulp.dest(appName + '/public/js'));
});

gulp.task('process-i18n', function () {
	return gulp.src(appName + '/app/**/*.json')
		.pipe(templateCache("i18n.js", {
			root: "app",
			module: "soundlink"
		}))
		.pipe(gulp.dest(appName + '/public/js'));
});

/**
 * Watch changes.
 */
gulp.task('watch', ['process'], function () {
	gulp.watch(appName + "/app/**/*.js", ['process-js']);
	gulp.watch(appName + "/app/assets/less/**/*.less", ['process-css']);
	gulp.watch(appName + "/app/**/*.html", ['process-html']);
	gulp.watch(appName + "/app/**/*.json", ['process-i18n']);
});

gulp.task('process', [
	'process-js',
	'process-third-js',
	'process-css',
	'process-html',
	'process-i18n'
]);

gulp.task('release', ['setProdMod', 'process']);
gulp.task('release-js' , ['setProdMod', 'process-js']);
gulp.task('release-css' , ['setProdMod', 'process-css']);

gulp.task('setProdMod', function () {
	isEnvProd = true;
});