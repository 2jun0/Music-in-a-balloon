export class HTTPError extends Error {
  statusCode: number;

  constructor(statusCode: number, messae?: string) {
    super(messae);

    this.name = 'HTTPError';
    this.statusCode = statusCode;

    Object.setPrototypeOf(this, HTTPError.prototype);
  }
}
