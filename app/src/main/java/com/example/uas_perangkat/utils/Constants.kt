package com.example.uas_perangkat.utils

/**
 * Object untuk menyimpan konstanta aplikasi
 */
object Constants {

    // API Configuration
    const val BASE_URL = "http://104.248.153.158/event-api/"
    const val CONNECTION_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds
    const val WRITE_TIMEOUT = 30L // seconds

    // Event Status
    const val STATUS_UPCOMING = "upcoming"
    const val STATUS_ONGOING = "ongoing"
    const val STATUS_COMPLETED = "completed"
    const val STATUS_CANCELLED = "cancelled"
    const val STATUS_ALL = "all"

    // Request Codes
    const val REQUEST_CREATE_EVENT = 1001
    const val REQUEST_EDIT_EVENT = 1002

    // SharedPreferences Keys
    const val PREFS_NAME = "event_management_prefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_NAME = "user_name"
    const val KEY_IS_LOGGED_IN = "is_logged_in"

    // Intent Extra Keys
    const val EXTRA_EVENT_ID = "event_id"
    const val EXTRA_EVENT_TITLE = "event_title"
    const val EXTRA_EVENT_DATA = "event_data"

    // Date & Time Format
    const val DATE_FORMAT_API = "yyyy-MM-dd"
    const val TIME_FORMAT_API = "HH:mm:ss"
    const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
    const val TIME_FORMAT_DISPLAY = "HH:mm"
    const val DATETIME_FORMAT_DISPLAY = "dd MMM yyyy, HH:mm"

    // Validation
    const val MIN_CAPACITY = 1
    const val MAX_CAPACITY = 10000
    const val MIN_TITLE_LENGTH = 3
    const val MAX_TITLE_LENGTH = 100
    const val MAX_DESCRIPTION_LENGTH = 500

    // Error Messages
    const val ERROR_NETWORK = "Tidak ada koneksi internet"
    const val ERROR_SERVER = "Terjadi kesalahan pada server"
    const val ERROR_TIMEOUT = "Koneksi timeout"
    const val ERROR_UNKNOWN = "Terjadi kesalahan tidak diketahui"
    const val ERROR_EMPTY_FIELD = "Field tidak boleh kosong"
    const val ERROR_INVALID_DATE = "Format tanggal tidak valid"
    const val ERROR_INVALID_TIME = "Format waktu tidak valid"
    const val ERROR_INVALID_CAPACITY = "Kapasitas harus antara $MIN_CAPACITY - $MAX_CAPACITY"

    // Success Messages
    const val SUCCESS_CREATE = "Event berhasil dibuat"
    const val SUCCESS_UPDATE = "Event berhasil diupdate"
    const val SUCCESS_DELETE = "Event berhasil dihapus"

    // Confirmation Messages
    const val CONFIRM_DELETE = "Apakah Anda yakin ingin menghapus event ini?"
    const val CONFIRM_CANCEL = "Apakah Anda yakin ingin membatalkan?"

    // Tab Positions
    const val TAB_ALL = 0
    const val TAB_UPCOMING = 1
    const val TAB_ONGOING = 2
    const val TAB_COMPLETED = 3
}