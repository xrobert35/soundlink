var gulp = require('gulp');
var gulpif = require('gulp-if');
var uglify = require('uglify-js');
var zip = require('gulp-zip');
var gutil = require('gulp-util');
var p = require('gulp-load-plugins')();
var less = require('gulp-less');
var LessAutoprefix = require('less-plugin-autoprefix');
var autoprefix = new LessAutoprefix({ browsers: ['last 2 versions'] });
var path = require('path');
var browserify = require('gulp-browserify');
var shell = require('gulp-shell');

var vendorOutputStream;

var appName = "soundlink";

function getEnv() {
	return "";
}

function envProd() {
	return false;
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
		'bower_components/angular-material-data-table/dist/md-data-table.js',
		'bower_components/angular-cookies/angular-cookies.js',
		'bower_components/angular-translate/angular-translate.js',
		'bower_components/angular-translate-loader-url/angular-translate-loader-url.js',
		'bower_components/angular-ui-router/release/angular-ui-router.js',
		'bower_components/angular-websocket/angular-websocket.js',
		'bower_components/ng-file-upload/ng-file-upload.js',
		'bower_components/ng-file-upload-shim/ng-file-upload.js',
		'bower_components/angular-sanitize/angular-sanitize.js',
		'bower_components/ui-router/release/angular-ui-router.js',
		'bower_components/moment/moment.js',
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

gulp.task('process-css', function () {
	var src = [
		appName + '/app/assets/less/**/*.less',
		'bower_components/material-design-icons/iconfont/material-icons.css',
		'bower_components/angular-material/angular-material.css',
		'bower_components/angular-material-data-table/dist/md-data-table.css'
	];

	return gulp.src(src)
		// .pipe(p.sourcemaps.init())
		.pipe(less({
			plugins: [autoprefix]
		}))
		.on('error', gutil.log)
		.pipe(p.concat('styles.css'))
		.pipe(gulpif(envProd(),
			p.sizereport({
				gzip: true,
				total: false,
				minifier: function (contents, filepath) {
					return new cleanCss().minify(contents).styles;
				}
			})
		))
		.pipe(gulpif(envProd(), p.cleanCss()))
		.pipe(gulp.dest(appName + '/public/css'));
});

function notifyLiveReload(event) {
  var fileName = require('path').relative(__dirname, event.path);
  tinylr.changed({
    body: {
      files: [fileName]
    }
  });
}

gulp.task('process-audio', function () {
	var src = ['audioPlayer/player/**/*.js'];

	return gulp.src(src)
		.pipe(p.iife({
			prependSemicolon: false,
			useStrict: false
		}))
		.pipe(p.angularInjector())
		.pipe(p.angularFilesort())

		// Avoid in production
		.pipe(gulpif(!envProd(), p.concatSourcemap('audioPlayer.js', {
			sourcesContent: true
		})))

		// Production environment
		.pipe(gulpif(envProd(), p.concat('audioPlayer.js')))
		.pipe(gulpif(envProd(), p.stripDebug()))
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
		.pipe(gulp.dest('audioPlayer/build'));
});


gulp.task("browseraudio", ['process-audio'], shell.task([
  'browserify audioPlayer/audioPlayer.js -o soundlink/public/js/audioPlayer.js',
	'xcopy audioPlayer\\build\\audioPlayer.js.map soundlink\\public\\js /y' 
]));

/**
 * Watch changes.
 */
gulp.task('watch', ['process'], function () {
	gulp.watch(appName + "/app/**/*.js", ['process-js']);
	gulp.watch(appName + "/app/assets/less/**/*.less", ['process-css']);
	gulp.watch('audioPlayer/**/*.js', ['browseraudio']);
	gulp.watch('public/**/*.html', notifyLiveReload);
	gulp.watch('public/css/*.css', notifyLiveReload);
	gulp.watch('public/js/*.js', notifyLiveReload);
});

gulp.task('process', [
	'process-js',
	'process-third-js',
	'process-css',
	'browseraudio'
]);