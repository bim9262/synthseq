package synthseq.signalanalysis;

public class FFT {
	/*
	 * This computes the frequency domain of the passed array. Returns 2d
	 * frequency domain of the passed array. Array length is a power of 2.
	 * Sampling frequency is assumed to be 44.1kHz
	 */
	public static double[][] FrequencyDomain(double[] x) {
		double[] y = new double[x.length];
		int m = (int) (Math.log(x.length) / Math.log(2));
		int n, i, i1, j, k, i2, l, l1, l2;
		double c1, c2, tx, ty, t1, t2, u1, u2, z;
		n = x.length;
		i2 = n >> 1;
		j = 0;
		// adapted from Paul Bourke's implementation
		// of a complex to complex FFT
		for (i = 0; i < n - 1; i++) {
			if (i < j) {
				tx = x[i];
				ty = y[i];
				x[i] = x[j];
				y[i] = y[j];
				x[j] = tx;
				y[j] = ty;
			}
			k = i2;
			while (k <= j) {
				j -= k;
				k >>= 1;
			}
			j += k;
		}
		c1 = -1.0;
		c2 = 0.0;
		l2 = 1;
		for (l = 0; l < m; l++) {
			l1 = l2;
			l2 <<= 1;
			u1 = 1.0;
			u2 = 0.0;
			for (j = 0; j < l1; j++) {
				for (i = j; i < n; i += l2) {
					i1 = i + l1;
					t1 = u1 * x[i1] - u2 * y[i1];
					t2 = u1 * y[i1] + u2 * x[i1];
					x[i1] = x[i] - t1;
					y[i1] = y[i] - t2;
					x[i] += t1;
					y[i] += t2;
				}
				z = u1 * c1 - u2 * c2;
				u2 = u1 * c2 + u2 * c1;
				u1 = z;
			}
			c2 = Math.sqrt((1.0 - c1) / 2.0);

			c2 = -c2;
			c1 = Math.sqrt((1.0 + c1) / 2.0);
		}
		for (i = 0; i < n; i++) {
			x[i] /= n;
			y[i] /= n;
		}
		//
		double[] power = new double[n / 2];
		for (i = 0; i < power.length; i++)
			power[i] = x[i] * x[i] + y[i] * y[i];
		double nyquist = 22050;
		double[] freq = new double[power.length];
		for (i = 0; i < freq.length; i++) {
			freq[i] = nyquist * ((double) i) / (freq.length);
		}
		double[][] res = new double[2][x.length];
		res[0] = freq;
		res[1] = power;
		return res;
	}
}
