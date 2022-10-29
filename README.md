# Hello world ClojureScript action

This action prints "Hello World" or "Hello" + the name of a person to greet to the log.

## Inputs

### `who-to-greet`

**Required** The name of the person to greet. Default `"world"`.

## Outputs

### `time`

The time we greeted you.

## Example usage

```yaml
uses: actions/cljs-action@v1.1
with:
  who-to-greet: 'Mona the Octocat'
```

