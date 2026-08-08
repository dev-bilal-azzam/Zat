import SwiftUI
import presentation

@main
struct iOSApp: App {
    init() {
        IosThumbnailBridge.shared.generateThumbNative = { filePath in
            let cleanPath = filePath.replacingOccurrences(of: "file://", with: "")
            let url = URL(fileURLWithPath: cleanPath)
            let asset = AVAsset(url: url)

            let durationSeconds = CMTimeGetSeconds(asset.duration)
            let midSeconds = (durationSeconds.isFinite && durationSeconds > 0) ? durationSeconds / 2.0 : 0.0
            let targetTime = CMTime(seconds: midSeconds, preferredTimescale: 600)

            let generator = AVAssetImageGenerator(asset: asset)
            generator.appliesPreferredTrackTransform = true

            do {
                let cgImage = try generator.copyCGImage(at: targetTime, actualTime: nil)
                let uiImage = UIImage(cgImage: cgImage)

                if let data = uiImage.jpegData(compressionQuality: 0.8) {
                    return data as NSData
                }
            } catch {
                print("Thumbnail generation error: \(error)")
            }

            return nil
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}