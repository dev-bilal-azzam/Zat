package com.devbilal.presentation.common.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.currentItem
import platform.AVFoundation.duration
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.seekToTime
import platform.AVKit.AVPlayerViewController
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSURL
import platform.darwin.NSEC_PER_SEC

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoPlayer(
    url: String,
    modifier: Modifier,
    play: Boolean
) {
    val player = remember {
        val nsUrl = NSURL.URLWithString(url) ?: NSURL.fileURLWithPath(url)
        AVPlayer(uRL = nsUrl)
    }

    val playerViewController = remember {
        AVPlayerViewController().apply {
            this.player = player
            this.showsPlaybackControls = true
        }
    }

    LaunchedEffect(play) {
        if (play) {
            player.play()
        } else {
            player.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.pause()
            player.replaceCurrentItemWithPlayerItem(null)
        }
    }

    UIKitViewController(
        factory = { playerViewController },
        modifier = modifier
    )
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun AudioPlayer(
    url: String,
    modifier: Modifier,
    play: Boolean,
    seekTo: Long?,
    onProgressUpdate: (Float, Long, Long) -> Unit,
    onCompletion: () -> Unit
) {
    val player = remember {
        val nsUrl = NSURL.URLWithString(url) ?: NSURL.fileURLWithPath(url)
        AVPlayer(uRL = nsUrl)
    }

    LaunchedEffect(play) {
        if (play) {
            player.play()
        } else {
            player.pause()
        }
    }

    LaunchedEffect(seekTo) {
        seekTo?.let {
            player.seekToTime(CMTimeMakeWithSeconds(it / 1000.0, NSEC_PER_SEC.toInt()))
        }
    }

    DisposableEffect(Unit) {
        val interval = CMTimeMakeWithSeconds(0.5, NSEC_PER_SEC.toInt())
        val observer = player.addPeriodicTimeObserverForInterval(interval, null) { time ->
            val current = CMTimeGetSeconds(time)
            val duration = player.currentItem?.duration?.let { CMTimeGetSeconds(it) } ?: 0.0
            if (duration > 0) {
                onProgressUpdate(
                    (current / duration).toFloat(),
                    (current * 1000).toLong(),
                    (duration * 1000).toLong()
                )
            }
        }

        val notificationObserver = NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemDidPlayToEndTimeNotification,
            `object` = player.currentItem,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            onCompletion()
        }

        onDispose {
            player.pause()
            player.removeTimeObserver(observer)
            NSNotificationCenter.defaultCenter.removeObserver(notificationObserver)
            player.replaceCurrentItemWithPlayerItem(null)
        }
    }
}
