export class HTTPError extends Error {
  statusCode: number;
  code?: number;

  constructor(statusCode: number, messae?: string, code?: number) {
    super(messae);

    this.name = 'HTTPError';
    this.statusCode = statusCode;
    this.code = code;

    Object.setPrototypeOf(this, HTTPError.prototype);
  }
}
