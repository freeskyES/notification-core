package com.es.notificationcore.presentation.service.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.domain.usecase.SaveNotiUseCase
import com.es.notificationcore.utils.GsonUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val saveNotiUseCase: SaveNotiUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val notiJson = inputData.getString("noti_data")

            notiJson?.let {
                val noti = GsonUtil.fromJson(notiJson, Noti::class.java)

                withContext(Dispatchers.IO) {
                    processNotification(noti)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Failed to process notification")
            Result.failure()
        }
    }

    private suspend fun processNotification(noti: Noti) {
        Timber.i("Processing notification: $noti")
        saveNotiUseCase.execute(noti)
    }
}