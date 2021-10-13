package it.mrschyzo.configuration

const val FILE_GENERATION = "file.generation"
const val FILE_GENERATION_SERVE_ROOT_KEY = "serveRoot"
const val FILE_GENERATION_SERVE_ROOT_DEFAULT = "http://localhost:8080"
const val FILE_GENERATION_URL_SUFFIX_KEY = "urlSuffix"
const val FILE_GENERATION_URL_SUFFIX_DEFAULT = ""

const val REST_GENERATION = "rest.generation"
const val REST_GENERATION_ROUTE_KEY = "route"
const val REST_GENERATION_ROUTE_DEFAULT = "http://localhost:8080/speak"

const val STORAGE_FILESYSTEM = "storage.filesystem"
const val STORAGE_FILESYSTEM_ROOT = "$STORAGE_FILESYSTEM.root"
const val STORAGE_FILESYSTEM_ROOT_DEFAULT = "."
const val STORAGE_FILESYSTEM_DEPTH_DEFAULT = "1"
