package com.example.buxiaohui.myapplication.download;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 监听进度
 */
public class DownLoadResponseBody extends ResponseBody {

    private DownloadItem downloadItem;
    private ResponseBody mResponseBody;
    private ResponseListener fileRespondResult;
    private BufferedSource bufferedSource;

    public DownLoadResponseBody(ResponseBody mResponseBody) {
        this.mResponseBody = mResponseBody;
    }

    public DownLoadResponseBody(DownloadItem downloadItem, ResponseBody mResponseBody, ResponseListener fileRespondResult) {
        this.downloadItem = downloadItem;
        this.mResponseBody = mResponseBody;
        this.fileRespondResult = fileRespondResult;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return bufferedSource;
    }

    public Source source(Source source) {
        return new ForwardingSource(source) {

            //当前读取字节数
            long bytesRead = 0L;
            //总字节长度
            long totalLength = 0L;

            int i = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if (downloadItem.getState() == DownloadTask.STATE_PAUSE || downloadItem.getState() == DownloadTask.STATE_WAITING) {
                    bytesRead = 0;
                    return bytesRead;
                }
                try {
                    bytesRead = super.read(sink, byteCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bytesRead != -1) {

                } else {
                    bytesRead = 0;
                }

                totalLength += bytesRead;
                downloadItem.setCurrentProgress(downloadItem.getCurrentProgress() + bytesRead);//实时更新downloadinfo的进度

                long progress = downloadItem.getCurrentProgress();
                long total = downloadItem.getFileLength();
                postMainThread(progress, total);
                return bytesRead;
            }
        };
    }

    private void postMainThread(long progress, long total) {
        fileRespondResult.onExecuting(progress, total,progress > total);
    }
}
