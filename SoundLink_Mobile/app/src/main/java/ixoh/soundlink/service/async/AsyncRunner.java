package ixoh.soundlink.service.async;

import android.os.AsyncTask;

/**
 * Created by Ixoh on 20/04/2016.
 */
public abstract class AsyncRunner<P, R> extends AsyncTask<P, Void, AsyncResponse<R>> {

    private AsyncCallBack<R> callBack;

    public AsyncRunner(AsyncCallBack<R> callBack) {
        this.callBack = callBack;
    }

    @Override
    protected AsyncResponse<R> doInBackground(P... params) {
        AsyncResponse<R> response = new AsyncResponse<R>();
        try {
            R result = run(params[0]);
            response.setSuccess(true);
            response.setResponse(result);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCause(e);
        }
        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(AsyncResponse<R> response) {
        if(response.isSuccess()) {
            callBack.onSuccess(response.getResponse());
        }else{
            callBack.onFailure(response.getCause());
        }
    }

    protected abstract R run(P params);
}
